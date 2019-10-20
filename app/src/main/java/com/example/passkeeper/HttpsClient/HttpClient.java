package com.example.passkeeper.HttpsClient;

import java.util.Map;

public interface HttpClient {
    HttpResponse get(String url) throws RequestException;
    HttpResponse get(String url, HttpParameter[] httpParameters) throws RequestException;
    HttpResponse get(String url, HttpParameter[] httpParameters, Map<String,String> requestHeaders) throws RequestException;
    HttpResponse post(String url) throws RequestException;
    HttpResponse post(String url, HttpParameter[] httpParameters) throws RequestException;
    HttpResponse post(String url, HttpParameter[] httpParameters, Map<String,String> requestHeaders) throws RequestException;
    HttpResponse post(String url, HttpParameter[] httpParameters, Map<String, String> requestHeaders, String body) throws RequestException;
    HttpResponse put(String url) throws RequestException;
    HttpResponse put(String url, HttpParameter[] httpParameters) throws RequestException;
    HttpResponse put(String url, HttpParameter[] httpParameters, Map<String,String> requestHeaders) throws RequestException;
    HttpResponse put(String url, HttpParameter[] httpParameters, Map<String, String> requestHeaders, String body) throws RequestException;
    HttpResponse delete(String url) throws RequestException;
    HttpResponse delete(String url, HttpParameter[] httpParameters) throws RequestException;
    HttpResponse delete(String url, HttpParameter[] httpParameters, Map<String,String> requestHeaders) throws RequestException;
}
