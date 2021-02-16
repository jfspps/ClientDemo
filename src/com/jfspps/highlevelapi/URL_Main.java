package com.jfspps.highlevelapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

// this class demonstrates the use of high-level APIs in Java (UDP and TCP classes used previously are low-level)
public class URL_Main {

    public static void main(String[] args){
//        demoBaseAndRelativeURIs();

//        demoURLWithStreams();

        // similar to demoURLWithStreams() except that one can set connection parameters before impl the stream
        demoURLConnectionClass();
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

            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream())
            );

            String line = "";
            while(line != null){
                line = inputStream.readLine();
                System.out.println(line);
            }
            inputStream.close();

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