package edu.project.utils;

import edu.project.models.Currency;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HTTP {
    private static final String CURRENCY_URL = "https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%f";
    private static final String CURRENCY_API_KEY =
        //<editor-fold desc="secret">
            "3c0e6951c655bcaea88f9e1f";
        //</editor-fold>
    private static final Pattern CURRENCY_PATTERN = Pattern.compile("\"conversion_result\":(\\d+\\.\\d+)");

    private HTTP() { }

    public static String getResponseBody(String url) {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
        HttpRequest request = HttpRequest
            .newBuilder()
            .uri(new URI(url))
            .GET()
            .build();
        return httpClient
            .send(request, HttpResponse.BodyHandlers.ofString())
            .body();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static String getCurrencyConversionResponse(Currency from, Currency to, double amount) {
        String requestURL = String.format(Locale.US, CURRENCY_URL, CURRENCY_API_KEY, from, to, amount);
        String responseBody = getResponseBody(requestURL);
        assert responseBody != null;
        Matcher matcher = CURRENCY_PATTERN.matcher(responseBody);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new RuntimeException("Ошибка сервера.");
    }
}
