<?xml version="1.0" encoding="UTF-8"?>
<!--
  Question 36: Write a program to illustrate XML and XSLT.
  books.xsl - XSLT 1.0 stylesheet that turns books.xml into an HTML table.
  Shows: xsl:template, xsl:for-each, xsl:sort, xsl:value-of, xsl:if, xsl:choose/when/otherwise,
         xsl:attribute, count(), sum() and format-number().
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" encoding="UTF-8" indent="yes"/>

  <xsl:template match="/">
    <html>
      <head>
        <title>Book Catalog (XSLT)</title>
        <style>
          body { font-family: Arial, sans-serif; margin: 20px; background: #f5f7fa; }
          h2 { color: #2c3e50; }
          table { border-collapse: collapse; background: #fff; }
          th, td { border: 1px solid #999; padding: 6px 10px; }
          th { background: #2c3e50; color: #fff; }
          tr.costly td { background: #fdecea; }
          .tag { background: #27ae60; color: #fff; padding: 1px 6px; border-radius: 3px; font-size: 12px; }
          .out { color: #c0392b; font-weight: bold; }
          .low { color: #e67e22; font-weight: bold; }
          .ok  { color: #27ae60; font-weight: bold; }
        </style>
      </head>
      <body>
        <h2>Library Book Catalog</h2>
        <p>Total books: <b><xsl:value-of select="count(library/book)"/></b> |
           Total copies in stock: <b><xsl:value-of select="sum(library/book/stock)"/></b></p>
        <table>
          <tr>
            <th>ID</th><th>Title</th><th>Author</th><th>Category</th>
            <th>Publisher</th><th>Year</th><th>Price (Rs.)</th><th>Stock</th>
          </tr>
          <!-- for-each loops over every book; sort orders by category then by price -->
          <xsl:for-each select="library/book">
            <xsl:sort select="@category"/>
            <xsl:sort select="price" data-type="number" order="descending"/>
            <tr>
              <!-- xsl:if: highlight books costing more than 1000 -->
              <xsl:if test="price &gt; 1000">
                <xsl:attribute name="class">costly</xsl:attribute>
              </xsl:if>
              <td><xsl:value-of select="@id"/></td>
              <td>
                <xsl:value-of select="title"/>
                <xsl:if test="year &gt;= 2021">
                  <xsl:text> </xsl:text><span class="tag">New</span>
                </xsl:if>
              </td>
              <td><xsl:value-of select="author"/></td>
              <td><xsl:value-of select="@category"/></td>
              <td><xsl:value-of select="publisher"/></td>
              <td><xsl:value-of select="year"/></td>
              <td align="right"><xsl:value-of select="format-number(price, '#,##0.00')"/></td>
              <td>
                <!-- xsl:choose: stock status -->
                <xsl:choose>
                  <xsl:when test="stock = 0"><span class="out">Out of stock</span></xsl:when>
                  <xsl:when test="stock &lt; 5"><span class="low">Only <xsl:value-of select="stock"/> left</span></xsl:when>
                  <xsl:otherwise><span class="ok"><xsl:value-of select="stock"/> available</span></xsl:otherwise>
                </xsl:choose>
              </td>
            </tr>
          </xsl:for-each>
        </table>
        <p style="font-size:13px">Rows in red cost more than Rs. 1000 (xsl:if). Sorted by category, then by price (highest first).</p>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
