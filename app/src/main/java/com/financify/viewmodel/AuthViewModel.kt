package com.financify.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import com.amazonaws.regions.Regions
import com.auth0.jwt.JWT
import com.financify.model.CLIENT_ID
import com.financify.model.LoggedUser
import com.financify.model.USER_POOL_ID
import com.financify.service.TokenDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(context: Context, private val tokenDataStore: TokenDataStore) :
    BaseViewModel() {
    private val _userSession = MutableStateFlow<CognitoUserSession?>(null);

    private val _loggedUser = MutableStateFlow<LoggedUser?>(null)
    var loggedUser: StateFlow<LoggedUser?> = _loggedUser.asStateFlow()
    private val _token = MutableStateFlow<String?>(null)
    var token: StateFlow<String?> = _token.asStateFlow()

    private val userPool = CognitoUserPool(
        context,
        USER_POOL_ID,
        CLIENT_ID,
        "",
        Regions.EU_WEST_3
    )

    init {
        viewModelScope.launch {
            tokenDataStore.token.collect { storedToken ->
                if (storedToken != null) {
                    _token.value = storedToken
                }
            }
        }
        viewModelScope.launch {
            tokenDataStore.idToken.collect { storedIdToken ->
                if (storedIdToken != null) {
                    val jwt = JWT.decode(storedIdToken)
                    val givenName = jwt.getClaim("given_name").asString()
                    val familyName = jwt.getClaim("family_name").asString()
                    _loggedUser.value = LoggedUser(
                        givenName ?: "",
                        familyName ?: ""
                    )
                }
            }
        }
    }

    fun authenticate(username: String, password: String) {
        viewModelScope.launch {
            setLoading(true)
            val userFromAws = userPool.getUser(username)
            userFromAws.getSessionInBackground(object : AuthenticationHandler {
                override fun onSuccess(
                    userSession: CognitoUserSession?,
                    newDevice: CognitoDevice?
                ) {
                    _userSession.value = userSession
                    _token.value = userSession?.accessToken?.jwtToken
                    userSession?.idToken?.jwtToken.let { token ->
                        val jwt = JWT.decode(token)
                        val givenName = jwt.getClaim("given_name").asString()
                        val familyName = jwt.getClaim("family_name").asString()
                        _loggedUser.value = LoggedUser(
                            givenName ?: "",
                            familyName ?: ""
                        )
                    }
                    viewModelScope.launch {
                        userSession?.accessToken?.jwtToken?.let { token ->
                            tokenDataStore.saveToken(token)
                        }
                    }
                    viewModelScope.launch {
                        userSession?.idToken?.jwtToken?.let { idToken ->
                            tokenDataStore.saveIdToken(idToken)
                        }
                    }
                    setLoading(false)
                    setSuccessMessage("Vous êtes logué avec succès")
                }

                override fun getAuthenticationDetails(
                    authenticationContinuation: AuthenticationContinuation?,
                    userId: String?
                ) {
                    val userDetails = AuthenticationDetails(userId, password, null)
                    authenticationContinuation?.setAuthenticationDetails(userDetails)
                    authenticationContinuation?.continueTask()
                }

                override fun getMFACode(continuation: MultiFactorAuthenticationContinuation?) {
                    TODO("Not yet implemented")
                }

                override fun authenticationChallenge(continuation: ChallengeContinuation?) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(exception: Exception?) {
                    setLoading(false)
                    _userSession.value = null
                    _loggedUser.value = null
                    setErrorMessage(exception?.message)
                }
            })
        }
    }

    fun logout() {
        viewModelScope.launch {
            setLoading(true)
            val currentUser = userPool.currentUser
            currentUser?.signOut()
            _userSession.value = null
            _loggedUser.value = null
            _token.value = null
            tokenDataStore.clearToken()
            setLoading(false)
            setSuccessMessage("Vous êtes délogué avec succès")
        }
    }
}