package com.jfspps.highlevelapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.Map;

// this class demonstrates the use of high-level APIs in Java (UDP and TCP classes used previously are low-level)
public class URL_Main {

    public static void main(String[] args){
//        demoBaseAndRelativeURIs();

//        demoURLWithStreams();

        // similar to demoURLWithStreams() except that one can set connection parameters before impl the stream
//        demoURLConnectionClass();

        // using HttpURlConnection instead of URLConnection
        demoHttpURLConnection();
    }

    private static void demoHttpURLConnection() {
        System.out.println(" ================== Demo of URLs with HttpURLConnection =====================================");

        try {
            URL url = new URL("http://google.co.uk");

            // this single connection can be funnelled to multiple objects
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // set any settings, as with URLConnection
            httpURLConnection.setRequestMethod("GET");      // GET is the default anyway
            httpURLConnection.setRequestProperty("User-Agent", "Chrome");
            httpURLConnection.setReadTimeout(5000);

            // note that getResponse() also performs the connection, hence connect() is redundant
            // there are other methods which can implicitly call connect()
            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("Response code: " + responseCode);

            if (responseCode != 200){
                System.out.println("Error reading page, timeout passed");
                return;
            }

            BufferedReader inputReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream())
            );

            String line;

            // checks inputReader.readLine() != null
            while((line = inputReader.readLine()) != null){
                System.out.println(line);
            }
            inputReader.close();

        } catch (MalformedURLException exception){
            System.out.println("URL problem: " + exception.getMessage());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    private static void demoURLConnectionClass() {
        System.out.println(" ================== Demo of URLs with URLConnection =====================================");

        try {
            URL url = new URL("http://google.co.uk");

            // returns an open connection in readiness to set conditions before connecting to the URL...
            URLConnection urlConnection = url.openConnection();

            // ...force the page to reload without reading from any cache and set the timeout limit
            urlConnection.setUseCaches(false);
            urlConnection.setReadTimeout(5000);

            // connect once settings have been configured
            urlConnection.connect();

            // get the header fields of the URL
            Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
            for(Map.Entry<String, List<String>> entry : headerFields.entrySet()){
                String key = entry.getKey();
                List<String> value = entry.getValue();
                System.out.println("Key: " + key);
                for (String output: value){
                    System.out.println("Value: " + value);
                }
            }
        } catch (MalformedURLException exception){
            System.out.println("URL problem: " + exception.getMessage());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    private static void demoURLWithStreams() {
        System.out.println(" ================== Demo of URLs with Java =====================================");

        try {
            URL url = new URL("http://google.co.uk");

            // convert to URI and perform the same operations
            URI uri = url.toURI();
            printComponents(uri);

            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );

            String line = "";
            while(line != null){
                line = inputStream.readLine();
                System.out.println(line);
            }
            inputStream.close();

        } catch (URISyntaxException exception) {
            System.out.println("URI problem: " + exception.getMessage());
        } catch (MalformedURLException exception){
            System.out.println("URL problem: " + exception.getMessage());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    private static void demoBaseAndRelativeURIs() {
        System.out.println(" ================== Demo of base and relative URIs =====================================");
        try {
            URI uri = new URI("db://username:password@myServer.com:5000/catalogue/phones?os=android#samsung");
            printComponents(uri);

            URI uri2 = new URI("helloThere");
            printComponents(uri2);

//            System.out.println(uri.toURL());    // should throw an exception since db is not valid scheme

            URI uri3 = new URI("http://username:password@myServer.com:5000/catalogue/phones?os=android#samsung");
            System.out.println(uri3.toURL());

            // relative URIs are handy when one wants to change one component of multiple URLs
            // (e.g. .com to .org, or, http to https) by implementing different uriRelatives's
            URI baseURI = new URI("http://username:password@myServer.com:5000");
            URI uriRelative = new URI("/catalogue/phones?os=android#samsung");

            URI urlResolved = baseURI.resolve(uriRelative);
            System.out.println(urlResolved.toURL());

            // extract the relative portion of a URI
            URI relativeURI = baseURI.relativize(urlResolved);
            System.out.println("Relative URI was: " + relativeURI);

        } catch (URISyntaxException exception){
            System.out.println("URI problem: " + exception.getMessage());
        } catch (MalformedURLException exception){
            System.out.println("URL problem: " + exception.getMessage());
        }
    }

    private static void printComponents(URI uri) {
        System.out.println(" Scheme: " + uri.getScheme());
        System.out.println(" Scheme-specific: " + uri.getRawSchemeSpecificPart());
        System.out.println(" Authority: " + uri.getAuthority());
        System.out.println(" User info: " + uri.getUserInfo());
        System.out.println(" Host: " + uri.getHost());
        System.out.println(" Port: " + uri.getPort());
        System.out.println(" Path: " + uri.getPath());
        System.out.println(" Query: " + uri.getQuery());
        System.out.println(" Fragment: " + uri.getFragment());
        System.out.println("====================================");
    }
}

// The nine components of a URI are explained at https://en.wikipedia.org/wiki/Uniform_Resource_Identifier
// URIs can be absolute or relative, whereas URLs are always absolute