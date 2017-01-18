   <!--User Editing allowed BELOW this line - DO NOT DELETE THIS LINE-->
   <xsl:template match="/" xml:id="id_11">
      <xsl:variable name="email" xml:id="id_12">
         <xsl:value-of select="concat('To: ', /nssrcmpr:MissingPersonReport/nssrcmpr:mailSettings/nssrcmpr:to)" xml:id="id_13"/>
         <xsl:text xml:id="id_14">&#xa;</xsl:text>
         <xsl:value-of select="concat('From: ', /nssrcmpr:MissingPersonReport/nssrcmpr:mailSettings/nssrcmpr:from)" xml:id="id_15"/>
         <xsl:text xml:id="id_16">&#xa;</xsl:text>
         <xsl:value-of select="'Subject: Missing Person Report'" xml:id="id_17"/>
         <xsl:text xml:id="id_18">&#xa;</xsl:text>
         <xsl:value-of select="'Content-Type: text/plain; charset=utf-8'" xml:id="id_19"/>
         <xsl:text xml:id="id_20">&#xa;</xsl:text>
         <xsl:value-of select="'MIME-Version: 1.0'" xml:id="id_21"/>
         <xsl:text xml:id="id_22">&#xa;</xsl:text>
         <xsl:text xml:id="id_23">&#xa;</xsl:text>
         <xsl:value-of select="'New missing person report:'" xml:id="id_24"/>
         <xsl:text xml:id="id_25">&#xa;</xsl:text>
         <xsl:text xml:id="id_26">&#xa;</xsl:text>
         <xsl:value-of xml:id="id_27"
                 select="concat(/nssrcmpr:MissingPersonReport/nssrcmpr:missingPerson/nssrcmpr:title, ' ', /nssrcmpr:MissingPersonReport/nssrcmpr:missingPerson/nssrcmpr:initials, ' (', /nssrcmpr:MissingPersonReport/nssrcmpr:missingPerson/nssrcmpr:firstName, ') ', /nssrcmpr:MissingPersonReport/nssrcmpr:missingPerson/nssrcmpr:lastName, ' is late for flight #', /nssrcmpr:MissingPersonReport/nssrcmpr:ident)"/>
         <xsl:text xml:id="id_28">&#xa;</xsl:text>
         <xsl:value-of xml:id="id_29"
                 select='concat ("Luggage Tag #", /nssrcmpr:MissingPersonReport/nssrcmpr:missingPerson/nssrcmpr:luggageTag )'/>
      </xsl:variable>
      <nstrgmpr:sendMsg xml:id="id_30">
         <nstrgmpr:Messages.definitions.requestPayLoadForSendMsg xml:id="id_31">
            <nsmpr0:raw xml:id="id_32">
               <xsl:value-of select="$email" xml:id="id_33"/>
            </nsmpr0:raw>
         </nstrgmpr:Messages.definitions.requestPayLoadForSendMsg>
         <nstrgmpr:QueryParameters xml:id="id_34">
            <nsmpr0:uploadType xml:id="id_35">media</nsmpr0:uploadType>
         </nstrgmpr:QueryParameters>
      </nstrgmpr:sendMsg>
   </xsl:template>