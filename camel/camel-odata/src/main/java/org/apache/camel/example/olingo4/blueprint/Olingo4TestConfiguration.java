/**
 *  Copyright 2005-2017 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.apache.camel.example.olingo4.blueprint;

import java.io.IOException;

import org.apache.camel.component.olingo4.Olingo4Configuration;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class Olingo4TestConfiguration extends Olingo4Configuration {

    public Olingo4TestConfiguration() {
        setServiceUri(getTestServiceUri("http://services.odata.org/TripPinRESTierService"));
        setContentType("application/json;charset=utf-8");
    }

    /*
    * Every request to the demo OData 4.0
    * (http://services.odata.org/TripPinRESTierService) generates unique
    * service URL with postfix like (S(drebz6ik9htrtrnu7tdjlwtv)) for each
    * session This method makes request to the base URL and return URL with
    * generated postfix
    */
    protected String getTestServiceUri(String baseUrl) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(baseUrl);
        HttpContext httpContext = new BasicHttpContext();
        try {
            httpclient.execute(httpGet, httpContext);
        } catch (IOException e) {
            throw new RuntimeException("Error getting OData Test service URI using base URL:" + baseUrl);
        }
        HttpUriRequest currentReq = (HttpUriRequest)httpContext.getAttribute("http.request");
        HttpHost currentHost = (HttpHost)httpContext.getAttribute("http.target_host");
        String currentUrl = currentHost.toURI() + currentReq.getURI();
        return currentUrl;
    }
}
