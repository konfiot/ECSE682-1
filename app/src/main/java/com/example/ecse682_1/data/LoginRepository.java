package com.example.ecse682_1.data;

import com.example.ecse682_1.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

//    private KeyPairGenerator kpg;
//
//    private KeyStore store = KeyStore.getInstance();

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = new LoginDataSource();
//        try {
//            kpg = KeyPairGenerator.getInstance(
//                        KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
//            kpg.initialize( new KeyGenParameterSpec.Builder(
//                        alias,
//                        KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
//                        .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
//                        .build()
//            );
//            KeyPair kp = kpg.generateKeyPair();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public Result<LoggedInUser> register(String username, String password) {
        try {
            dataSource.register(username, password);
            return new Result.Success<LoggedInUser>( new LoggedInUser(username, password));
        } catch(Exception e) {
            return new Result.Error( e );
        }
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        System.out.println("Repo longgin "+username);
        if(isLoggedIn()){
            return new Result.Error(new Exception("A user is already logged in"));
        }
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }

    public void end() {
        dataSource.safeState();
    }

}
