[Report]
Title=Ticket - Padrão - Impressão Rápida
Width=132
Height=44
LineSpacing=8

[LayOut]

001,01== = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
091,02=                                                                                          Data....:
063,03=                                                              Fone:                       Pedido..:
091,04=                                                                                          Vendedor:
001,05=- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
002,06= Cliente.:                                                                                   Fone:              Fax:
002,07= Endereco:                                                 Bairro:                           CEP:
002,08= Cidade..:                                    UF:          CPF/CNPJ:                         RG/Inscr.Est.:
001,09=- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
002,10= Codigo Descricao                                                                        Quantidade   Und   Unitario      Total
001,11=+ - - -+- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -+- - - - - - + - - + - - - - -+- - - - - - -

001,30=- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
002,31=Formas de Pagamento:                                                                                 Desconto......: ************
103,32=                                                                                                     Mercadorias...: ************
103,33=                                                                                                     Servicos......: ************
103,34=                                                                                                     Frete.........: ************
103,35=                                                                                                     Encargos......: ************
103,36=                                                                                                     Despesas......: ************
103,37=                                                                                                     ----------------------------
103,38=                                                                                                     Total.........: ************
001,39== = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
002,40= Observacoes:                                                                                        
001,42=*Nao e documento fiscal. Documento nao e valido como garantia da mercadoria.

  



[Empresa]
Fantasia=Left=2,Top=2
Endereco=Left=2,Top=3,"Format=?s+', '+IntFormat(Numero,6)"
Telefone=Left=68,Top=3

[Nota Fiscal]
Data=Left=101,Top=2,"Format=FormatDateTime('dd/mm/yyyy', ?s)"
Nota=Left=101,Top=3,"Format=IntFormat(?n, 7, '.')+'/'+IntFormat(Filial,2,'0')"
Tipo=Left=2,Top=4,"Format='Comprovante de ' + if(?s='A','entrega','devolucao') + ' de mercadorias'"
Observacao,1=Left=15,Top=40,lineWrap=115,MaxHeight=2

[Parceiro]
Nome=Left=12,Top=6,"Format=if(?s='',Fantasia,Nome) + '(' + IntFormat(Parceiro) + '/' + IntFormat(Filial,2,0)+')'"
Telefone=Left=99,Top=6
Fax=Left=117,Top=6

Endereco=Left=12,Top=7,"Format=?s+', '+IntFormat(Numero,6)"
Bairro=Left=68,Top=7
CEP=Left=99,Top=7

Municipio=Left=12,Top=8
UF=Left=51,Top=8

Tipo_Pessoa,1=Left=70,Top=8,"Format=if(?s='F',if(CPF<>'0  .   .   -',CPF),CNPJ)"
Tipo_Pessoa,2=Left=109,Top=8,"Format=if(?s='F',RG,Inscricao_Estadual)"

[Vendedor]
Nome=Left=101,Top=4,"Format=copy(?s, 1, 30)"

[Itens]
_Start=12
_Count=18
_Height=1

Item=Left=3
Descricao=Left=10,"Format=?s + Complemento",LineWrap=77,MaxHeight=10
Quantidade=Left=93,"Format=CurrFormat(?n, 8.3, '')"
Unidade=Left=103
Unitario=Left=109,"Format=CurrFormat(?n, 8.2, '')"
Total=Left=121,"Format=CurrFormat(?n, 11.2, '')"

[Totais]
Desconto=Left=119,Top=31,"Format=CurrFormat(?n, 12.2, '')+'-'"
Produtos=Left=119,Top=32,"Format=CurrFormat(?n, 12.2, '')+'+'"
Servicos=Left=119,Top=33,"Format=CurrFormat(?n, 12.2, '')+'+'"
Frete=Left=119,Top=34,"Format=CurrFormat(?n + Entrega, 12.2, '')+'+'"
Encargos=Left=119,Top=35,"Format=CurrFormat(?n, 12.2, '')+'+'"
Despesas=Left=119,Top=36,"Format=CurrFormat(?n, 12.2, '')+'+'"
Total,2=Left=119,Top=38,"Format=CurrFormat(?n, 12.2, '')"

[Parcelas]
_Start=32
_Count=6
_Columns=2
_Width=32

Tipo=Left=3,"Format=if(?s='$', 'DIN', if(?s='A', 'DUP', if(?s='B', 'CRN', if(?s='C', 'C/C', if(?s='D', 'CHQ', if(?s='E', 'CAR', if(?s='F', 'CNV', 'DIN')))))))"
Vencimento=Left=7,"Format=FormatDateTime('dd/mm/yyyy', ?s)"
Valor=Left=16,"Format=CurrFormat(?n, 11.2, '')"

;[MMC]
;A_PRAZO_EXTENSO=Left=2,Top=2
;TOTAL_EXTENSO=Left=2,Top=3,"Format=?s+', '+IntFormat(Numero,6)"
