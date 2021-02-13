package com.jfspps.highlevelapi;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

// this class demonstrates the use of high-level APIs in Java (UDP and TCP classes used previously are low-level)
public class URL_Main {

    public static void main(String[] args){
        try {
            URI uri = new URI("db://username:password@myServer.com:5000/catalogue/phones?os=android#samsung");
            printComponents(uri);

            URI uri2 = new URI("helloThere");
            printComponents(uri2);

//            System.out.println(uri.toURL());    // should throw an exception since db is not valid scheme

            URI uri3 = new URI("http://username:password@myServer.com:5000/catalogue/phones?os=android#samsung");
            System.out.println(uri3.toURL());

            // relative URIs are handy when one wants to change one component of multiple URLs (e.g. .com to .org, or, http to https)
            URI baseURI = new URI("http://username:password@myServer.com:5000");
            URI uriRelative = new URI("/catalogue/phones?os=android#samsung");

            URI urlResolved = baseURI.resolve(uriRelative);
            System.out.println(urlResolved.toURL());

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