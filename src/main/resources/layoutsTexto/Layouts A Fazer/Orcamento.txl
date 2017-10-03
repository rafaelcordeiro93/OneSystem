[Report]
Title=Orcamento - Padrao
Width=132
Height=33

[LayOut]
1,01=====================================================================================================================================
1,02=.                                                       O R C A M E N T O
1,03=.                                                                                                                    Num.:
1,04=.                                                                                                                    Data:
1,05=====================================================================================================================================
1,06=Cliente..:                                                                 Codigo...:                  Vendedor:
1,07=Endereco.:                                   Bairro:                       Municipio:                                          UF:
1,08=Fone.....:               Fax:
1,09=Codigo | Descricao                                                                        | Quantidade | Und | Unitario |      Total
1,10=-------+----------------------------------------------------------------------------------+------------+-----+----------+-----------
1,11=-      |                                                                                  |            |     |          |
1,12=-      |                                                                                  |            |     |          |
1,13=-      |                                                                                  |            |     |          |
1,14=-      |                                                                                  |            |     |          |
1,15=-      |                                                                                  |            |     |          |
1,16=-      |                                                                                  |            |     |          |
1,17=-      |                                                                                  |            |     |          |
1,18=-      |                                                                                  |            |     |          |
1,19=-      |                                                                                  |            |     |          |
1,20=-      |                                                                                  |            |     |          |
1,21=-      |                                                                                  |            |     |          |
1,22=-      |                                                                                  |            |     |          |
1,23=-      |                                                                                  |            |     |          |
1,24=-      |                                                                                  |            |     |          |
1,25=-      |                                                                                  |            |     |          |
1,26=-      |                                                                                  |            |     |          |
1,27=-------+----------------------------------------------------------------------------------+------------+-----+----------+-----------
1,28=Validade...:                                                                                       Total......:
1,29=Condicoes..:
1,30=Observacoes:
1,31=====================================================================================================================================
2,32=  Nao e documento fiscal
2,33=  Documento nao e valido como garantia da mercadoria

[Empresa]
Fantasia=Left=1,Top=3
Endereco=Left=1,Top=4,"Format=?s + ' ' + IntFormat(Numero,6)"
Telefone=Left=25,Top=4,"Format='Fone: ' + ?s"

[Orcamento]
Orcamento=Left=123,Top=3,"Format=IntFormat(?n, 10, '.')",FontSize=10
Data=Left=123,Top=4,"Format=FormatDateTime('dd/mm/yyyy', ?s)",FontSize=10

Cliente=Left=87,Top=6,"Format=IntFormat(?n, 7, '.') + '-'"
Filial_Cliente=Left=95,Top=6,"Format=IntFormat(?n, 2, '0')"
Nome=Left=12,Top=6
Endereco=Left=12,Top=7,"Format=Endereco + ', ' + Str(Numero)"
Bairro=Left=54,Top=7
Municipio=Left=87,Top=7
UF=Left=131,Top=7
Fone=Left=12,Top=8
Fax=Left=31,Top=8

[Total]
Total=Left=113,Top=28,"Format=CurrFormat(?n, 12.2, '')"

[Dados Adicionais]
Validade=Left=14,Top=28
Condicoes=Left=14,Top=29
Observacoes=Left=14,Top=30

[Vendedor]
Nome=Left=114,Top=6

[Itens]
_Start=11
_Count=16
_Height=1
Item=Left=1,"Format=IntFormat(?n, 5, '.')"
Descricao=Left=10,"Format=TrimRight(Str(?s))+'   '+TrimRight(Complemento)",LineWrap=60,MaxHeight=3
Quantidade=Left=93,"Format=CurrFormat(?n, 10.3, '')"
Unidade=Left=106
Unitario=Left=111,"Format=CurrFormat(?n, 9.2, '')"
Total=Left=123,"Format=CurrFormat(?n, 10.2, '')"

