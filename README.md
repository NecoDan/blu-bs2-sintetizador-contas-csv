# Projeto - Análise Dados em Lote {CSV} - © 2020 Blu by BS2 System.
  Projeto em Kotlin de um leitor e processamentos de arquivos CSV contendo transações bancárias, voltado à atender o desafio da © 2020 Blu By BS2 <link>https://blubybs2.com/.
   
  Uma solução criada em Kotlin. Atendendo como um sistema de análise de dados, onde o sistema deve ```processar arquivos CSV de transferência de contas bancárias importar, ler e analisar os dados e produzir um arquivo de saída```  como um ```relatório sintético``` . 
  
  De sorte que, dando uma ```entrada CSV```:
   - ```Conta; Valor ``` 
   - ```1234-3;75.00 ``` 
   - ```1234-3;75.00 ```
   - ```1235-6;100.00```
   - ```1236-0;100.00```
   
  Irá ser produzida uma saída como ```relatório sintético```: 
   - ```Conta; Depósitos; Total de Bônus ``` 
   - ```1234-3; 100.00; 10.00 ``` 
   - ```1235-6; 100.00; 5.00 ```
   - ```1236-0; 100.00; 1.00 ```

 #### Stack do projeto
  - Escrito em Kotlin;
  - Utilizando as facilidades e recursos framework da própria linguagem Kotlin;
  - Boas práticas de programação, utilizando Design Patterns (Strategy);
  - Testes unitátios (TDD).
   
 #### Visão Geral
  
  A aplicaçao tem como objetivo ler cada linha de registro de arquivo CSV válido com um layout específico a partir das colunas com o número da conta e transações. 
  
  Processá-los e gerar uma saída no output console e também via arquivo {CSV} contendo:
    
   - Conta com [numero-digito]; 
   - Saldo atual da conta;
   - O valor de percentual do total de bônus que deve ser creditado. 
  
 #### Instruções Inicialização
  
    1. Clone o repositório git@github.com:NecoDan/blu-bs2-sintetizador-contas-csv.git
    
    2. Ou faça o download do arquivo ZIP do projeto em https://github.com/NecoDan/blu-bs2-sintetizador-contas-csv
        
    3. Importar o projeto em sua IDE de preferência (lembre-se, projeto baseado e desenvolvido em Kotlin)
    
    4. Buildar o projeto e executá-lo.
  
 #### Autor e mantenedor do projeto
 - Daniel Santos Gonçalves - Bachelor in Information Systems, Federal Institute of Maranhão - IFMA / Software Developer Fullstack.
 - GitHub: https://github.com/NecoDan
 
 - Linkedin: <link>https://www.linkedin.com/in/daniel-santos-bb072321 
 
 - Twiter: <link>https://twitter.com/necodaniel. 
