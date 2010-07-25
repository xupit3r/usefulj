<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" omit-xml-declaration="yes"/>
	<xsl:strip-space elements="*"/>
	<xsl:template match="/">
		<xsl:apply-templates/>
	</xsl:template>

<!-- Matched Templates -->
	
	<xsl:template match="class">
		<xsl:value-of select="@modifier"/> 
		<xsl:call-template name="addSpaces">
			<xsl:with-param name="numSpaces" select="1"/>
		</xsl:call-template>
		<xsl:text>class</xsl:text>
		<xsl:call-template name="addSpaces">
			<xsl:with-param name="numSpaces" select="1"/>
		</xsl:call-template>
		<xsl:value-of select="@name"/>
		<xsl:text>{</xsl:text>
		<xsl:apply-templates/>
		<xsl:text>}</xsl:text>
	</xsl:template>
	
	<xsl:template match="variable">
	  <xsl:text>
		<xsl:choose>
			<xsl:when test="@type">
				<xsl:value-of select="@type"/> 
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>Object</xsl:text>
			</xsl:otherwise>
		</xsl:choose> 
		<xsl:call-template name="addSpaces">
			<xsl:with-param name="numSpaces" select="1"/>
		</xsl:call-template>
		<xsl:value-of select="@name"/>
		<xsl:call-template name="addSpaces">
			<xsl:with-param name="numSpaces" select="1"/>
		</xsl:call-template> 
		<xsl:text>=</xsl:text>
		<xsl:call-template name="addSpaces">
			<xsl:with-param name="numSpaces" select="1"/>
		</xsl:call-template>
		<xsl:choose>
			<xsl:when test="@init">
				<xsl:value-of select="@init"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>null</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:text>;</xsl:text>
	  </xsl:text>
	</xsl:template>
	
	<!-- Named Templates -->

	<xsl:template name="addSpaces">
	 <xsl:param name="numSpaces" select="0"/>
		<xsl:choose>
			<xsl:when test="$numSpaces = 0"/>
			<xsl:otherwise>
				<xsl:text> </xsl:text>
				<xsl:call-template name="addSpaces">
					<xsl:with-param name="numSpaces" select="$numSpaces - 1"/>
				</xsl:call-template>			
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>