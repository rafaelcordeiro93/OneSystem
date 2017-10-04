[Report]
Title=Condicional - Modelo 1
Width=160
Height=44
LineSpacing=8

[LayOut]
001,01================================================================================================================================================================
129,03=                                                                                                                                Data:
129,04=                                                                                                                                Pedido:
001,05=---------------------------------------------------------------------------------------------------------------------------------------------------------------
001,06=Cliente..:                                                                 Codigo...:                  Vendedor:
001,07=Endereco.:                                     Bairro:                     Municipio:                                            UF:
001,08=Fone.....:               Fax:                  CPF/CNPJ:                   Insc.Est.:
001,09=Codigo    Descricao                                                                                                 Quantidade   Und     Unitario        Total
001,10=---------------------------------------------------------------------------------------------------------------------------------------------------------------
001,38=---------------------------------------------------------------------------------------------------------------------------------------------------------------
001,39=Observacoes:                                                                                             Acrescimo..:                     ************
106,40=                                                                                                         Desconto...:                     ************
001,41=Nao e documento fiscal. Este documento nao e valido como garantia da mercadoria.                         Total......:                     ************
001,42================================================================================================================================================================


[Empresa]
Fantasia=Left=1,Top=2
Endereco=Left=1,Top=3,"Format=?s + '  ' + IntFormat(Numero,6)"
Telefone=Left=70,Top=3,"Format='   Fone: ' + ?s"

[Condicional]
Devolucao=Left=1,Top=4,"Format='Comprovante de ' + if(?s='S','devolucao','entrega') + ' de mercadorias - CONDICIONAL'"
Comanda=Left=143,Top=4,"Format=IntFormat(?n, 7, '.')+'/'+IntFormat(Filial, 4, '0')"
Data=Left=141,Top=3,"Format=FormatDateTime('dd/mm/yyyy', ?s)"
Observacao,1=Left=16,Top=39,FontSize=17,"Format=copy(?s, 1, 80)"
Observacao,2=Left=1,Top=40,FontSize=17,"Format=copy(?s, 81, 160)"


[Parceiro]
Parceiro=Left=87,Top=6,"Format=IntFormat(?n, 7, '0.') + '-' + IntFormat(Filial, 1, '0')"
Nome=Left=12,Top=6,FontSize=12
Endereco=Left=12,Top=7,"Format=?s + ' ' + IntFormat(Numero,2)"
Bairro=Left=56,Top=7
Municipio=Left=89,Top=7
UF=Left=134,Top=7
Telefone=Left=12,Top=8
Fax=Left=31,Top=8
CPF=Left=58,Top=8,"Format=if(?s='0  .   .   -','',?s)"
CNPJ=Left=69,Top=8
Inscricao_Estadual=Left=89,Top=8

[Vendedor]
Nome=Left=114,Top=6

[Itens]
_TamanhoDescricao=60
_Start=11
_Count=28
_Height=1
Item=Left=1,"Format=IntFormat(?n, 7, '.')"
Descricao=Left=11,"Format=Copy(?s + ' ' + Complemento,1,50)"
Quantidade=Left=113,"Format=CurrFormat(?n, 13.3, '')"
Unidade=Left=130
Unitario=Left=135,"Format=CurrFormat(?n, 10.2, '')"
Total=Left=148,"Format=CurrFormat(?n, 10.2, '')"

[Total]
Acrescimo=Left=139,Top=39,"Format=CurrFormat(?n, 12.2, '')"
Desconto=Left=139,Top=40,"Format=CurrFormat(?n, 12.2, '')"
Total=Left=139,Top=41,"Format=CurrFormat(?n, 12.2, '')"
