package vn.nano.core_library.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * Created by alex on 9/4/17.
 */

public class ApiErrorUtils {

    public static <T extends ApiError> String getError(Throwable throwable, Class<T> clazz) {
        if (throwable instanceof UnknownHostException
                || throwable instanceof ConnectException
                || throwable instanceof SocketTimeoutException) { // internet connection error
            return "error_internet_connection";
        } else if (throwable instanceof JsonSyntaxException) {
            return "error_data_format";
        } else if (throwable instanceof HttpException) { // HTTP exception (code != 200)
            try {
                T errorMessage = new Gson().fromJson(((HttpException)throwable).response().errorBody().string(), clazz);
                return errorMessage.getErrorMessage();
            } catch (JsonSyntaxException e) {
                return "error_data_format";
            } catch (IOException e) {
                return "error_unknown";
            }
        } else {                                        // unknown reason
            return "error_unknown";
        }
    }

    public abstract static class ApiError {
        protected abstract String getErrorMessage();
    }
}
