package producer;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import test.Tests;

@RunWith(Arquillian.class)
public class HttpServletResponseProducerTest {

	private static final String PATH = "src/test/resources/producer";

	@ArquillianResource
	private URL deploymentUrl;

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return Tests.createDeployment().addClass(RequestServlet.class)
				.addAsWebInfResource(Tests.createFileAsset(PATH + "/web.xml"), "web.xml");
	}

	@Test
	public void createResponse() throws ClientProtocolException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();

		HttpGet get = new HttpGet(deploymentUrl + "/responseproducer");
		HttpResponse response = client.execute(get);

		int status = response.getStatusLine().getStatusCode();
		assertEquals(HttpStatus.SC_OK, status);
	}

}