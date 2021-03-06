package io.mincongh.xml.xpath;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Stack Overflow: Get elements by tag name in xml parsing, excluding children of some parents
 *
 * @author Mincong Huang
 */
public class So50591626Test {

  private static final String XML =
      "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
          + "<article>\n"
          + "  <sub-article id=\"S01\" article-type=\"translation\" xml:lang=\"pt\">\n"
          + "    <front-stub>\n"
          + "      <article-categories>\n"
          + "        <subj-group subj-group-type=\"heading\">\n"
          + "          <subject>Artigos Originais</subject>\n"
          + "        </subj-group>\n"
          + "      </article-categories>\n"
          + "      <title-group>\n"
          + "        <article-title>\n"
          + "            Prevalência de deficiência nutricional em pacientes com\n"
          + "            tuberculose pulmonar\n"
          + "         <xref ref-type=\"fn\" rid=\"fn02\">*</xref>\n"
          + "        </article-title>\n"
          + "      </title-group>\n"
          + "    </front-stub>\n"
          + "  </sub-article>\n"
          + "  <article-meta>\n"
          + "    <article-id pub-id-type=\"pmid\">24068270</article-id>\n"
          + "    <article-id pub-id-type=\"pmc\">4075858</article-id>\n"
          + "    <article-id pub-id-type=\"publisher-id\">S1806-37132013000400012</article-id>\n"
          + "    <article-id pub-id-type=\"doi\">10.1590/S1806-37132013000400012</article-id>\n"
          + "    <article-categories>\n"
          + "      <subj-group subj-group-type=\"heading\">\n"
          + "        <subject>Original Articles</subject>\n"
          + "      </subj-group>\n"
          + "    </article-categories>\n"
          + "    <title-group>\n"
          + "      <article-title>\n"
          + "           Prevalence of nutritional deficiency in patients with\n"
          + "           pulmonary tuberculosis\n"
          + "           <xref ref-type=\"fn\" rid=\"fn01\">*</xref>\n"
          + "      </article-title>\n"
          + "    </title-group>\n"
          + "  </article-meta>\n"
          + "</article>\n";

  private Document document;

  @Before
  public void setUp() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try (InputStream in = new ByteArrayInputStream(XML.getBytes(StandardCharsets.UTF_8))) {
      document = factory.newDocumentBuilder().parse(in);
    }
  }

  @Test
  public void include() throws Exception {
    XPath xPath = XPathFactory.newInstance().newXPath();
    XPathExpression expr = xPath.compile("//article-meta//title-group");
    NodeList titleNodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
    assertThat(titleNodes.getLength()).isEqualTo(1);
    assertThat(titleNodes.item(0).getTextContent().trim()).startsWith("Prevalence of nutritional");
  }

  @Test
  public void exclude() throws Exception {
    XPath xPath = XPathFactory.newInstance().newXPath();
    XPathExpression expr = xPath.compile("/article/*[not(self::sub-article)]//title-group");
    NodeList titleNodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
    assertThat(titleNodes.getLength()).isEqualTo(1);
    assertThat(titleNodes.item(0).getTextContent().trim()).startsWith("Prevalence of nutritional");
  }
}
