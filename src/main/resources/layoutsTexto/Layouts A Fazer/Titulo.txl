[Report]
Title=Duplicata - 96 Colunas
Width=96
Height=33

[Layout]
1,01=+------------------------------------------+--------------------------------------------------+
1,02=|                                          |                                                  |
1,03=|                                          |                                                  |
1,04=|                                          |                                                  |
1,05=|                                          |                                                  |
1,06=|  Data de emissao:                        |                                                  |
1,07=+------------------------------+-----------+-----------------+------------------+-------------+
1,08=|          F A T U R A         |       D U P L I C A T A     |     Data  do     |             |
1,09=|      Valor      |   Numero   |       Valor      |  Numero  |    Vencimento    | Parcela No. |
1,10=+-----------------+------------+------------------+----------+------------------+-------------|
1,11=|                 |            |                  |          |                  |             |
1,12=+-----------------+------------+------------------+----------+------------------+-------------|
1,13=|      Sacado...:                                                  Codigo:                    |
1,14=|      Endereco.:                                                                             |
1,15=|      Municipio:                            CEP:                  Estado:                    |
1,16=|      Telefone.:                            Praca Pgto:                                      |
1,17=|      RG/IE....:                            CPF/CNPJ..:                                      |
1,18=|                                                                                             |
1,19=|     Valor Extenso:                                                                          |
1,20=|                                                                                             |
1,21=|                                                                                             |
1,22=|     Reconheco(emos) a exatidao desta duplicata de venda mercantil na importancia acima      |
1,23=|     que pagarei(emos) a loja, ou a sua ordem na praca e vencimentos acima indicados.        |
1,24=|                                                                                             |
1,25=|                                                                                             |
1,26=|                                                                                             |
1,27=|                                                                                             |
1,28=|   ________________________________      ____/____/____      _____________________________   |
1,29=|         Assinatura Emitente             Data do Aceite            Assinatura Sacado         |
1,30=+---------------------------------------------------------------------------------------------+

[Emitente]
Nome=Left=4,Top=2
Fantasia=Left=4,Top=4
Endereco=Left=47,Top=3,"Format=?s + ' ' + IntFormat(Numero) + ' ' + Bairro + ' ' + Complemento"
CEP=Left=47,Top=4
Municipio=Left=60,Top=4,"Format=?s + ' ' + UF"
Municipio,1=Left=59,Top=16
Telefone=Left=47,Top=5,"Format='Fone: ' + ?s"
Cnpj=Left=47,Top=6,"Format='CNPJ: ' + ?s "
Inscricao_Estadual=Left=73,Top=6,"Format='IE: ' + ?s"

[Origem]
Valor=Left=6,Top=11,"Format=CurrFormat(?n,11.2,'')"

[Conta a Receber]
NF=Left=22,Top=11,"Format=IntFormat(?n,6,''0'')"
Emissao=Left=22,Top=6,"Format=DateFormat('dd/mm/yyyy', ?s)"
Valor=Left=36,Top=11,"Format=CurrFormat(?n,13.2,'')"
Valor,1=Left=23,Top=19,"Format=Copy(CurrToR$(?n, 146),1,65)"
Valor,2=Left=7,Top=20,"Format=Copy(CurrToR$(?n, 146),66,85)"
Conta=Left=53,Top=11,"Format=IntFormat(?n,6,''0'') + '-' + IntFormat(Filial,1,''0'')"
Vencimento=Left=65,Top=11,"Format=DateFormat('dd / mm / yyyy', ?s)"
Parcela=Left=87,Top=11

[Sacado]
Nome=Left=20,Top=13
Parceiro=Left=77,Top=13,"Format=IntFormat(?n,6) + '-' + IntFormat(Filial)"
Endereco=Left=20,Top=14,"Format=?s + ' ' + IntFormat(Numero) + ' ' + Complemento + ' ' + Bairro"
Municipio=Left=20,Top=15
CEP=Left=52,Top=15
UF=Left=77,Top=15
Telefone=Left=20,Top=16
RG=Left=20,Top=17,"Format=if(Tipo_pessoa = 'J',Inscricao_estadual,RG)"
CPF=Left=59,Top=17,"Format=if(Tipo_pessoa = 'J',CNPJ,CPF)"
