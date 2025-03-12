Nome: Guilherme Pinheiro
Link para o vídeo demonstrativo: https://drive.google.com/file/d/1xQijxNopTcTnzdGFBgvB8mID9W3kZ1w8/view?usp=sharing

Introdução
Essa atualização teve como objetivos principais:
Implementar a leitura e escrita de dados no formato XML.
Tornar a interface de registro de consultas mais intuitiva para o usuário, facilitando a interação.

Comentário do Professor:
A lógica de persistência em arquivo a cada operação foi feita em formato JSON. A ideia era prover testes de leitura e escrita dos padrões JSON e XML (esta última em falta). A tela de consultas está ok, relacionando médico e paciente via ID, porém esta visão numa interface deveria trazer os nomes destes (uso de ID serve ao relacionamento de dados, não é intuitiva para interface com usuário).

Novas Features
-Implementação da persistência de dados em XML.
-Adição de uma opção no menu principal para o usuário escolher entre carregar dados em formato JSON ou XML.
-Melhorias na interface de registro de consultas, substituindo campos de texto para IDs de pacientes e médicos por elementos mais intuitivos.


Relato do Processo de Atualização do Código
1. Escolha da API para XML
O primeiro passo foi selecionar a API para manipulação de XML. Optei pela combinação de Jakarta XML Binding (jakarta.xml.bind-api) e GlassFish JAXB (org.glassfish.jaxb), que fornece a implementação em tempo de execução necessária para a API.

2. Adaptação das Classes de Serviço
Com a biblioteca escolhida, pesquisei como implementar as classes de serviço para XML seguindo a mesma lógica das classes de serviço JSON.

Utilizei o Unmarshaller para converter o conteúdo do arquivo XML em objetos Java no método de carregamento, preenchendo as listas (ArrayLists).
Para salvar os dados, usei o Marshaller para transformar as listas em arquivos XML formatados.
A anotação necessária para serialização no formato XML exigiu ajustes nas classes models.

Adicionei @XmlRootElement na definição de cada classe.
Adicionei a anotação @XmlElement nos métodos get de cada atributo.
3. Persistência e Tratamento de Erros
Inicialmente, implementei a persistência. A ideia era estabilizar essa funcionalidade antes de aplicá-la às outras classes. Durante o processo, enfrentei diversos erros, como:

Erros de Módulo: Tive que ajustar as configurações do módulo para garantir que os pacotes fossem acessíveis pela API jakarta.xml.bind e pela implementação GlassFish JAXB.
Inacessibilidade de Propriedades: Alguns atributos das classes não eram acessíveis automaticamente devido à estrutura dos módulos.
Erros de Serialização/Deserialização: As classes models não possuíam construtores sem argumentos, que são obrigatórios para o funcionamento do JAXB.
Após várias iterações e testes, consegui salvar os registros corretamente no arquivo XML.
4. Implementação de Carregar e Atualizar
Depois de implementar a funcionalidade de salvar, adicionei os métodos para carregar e atualizar os dados, garantindo a consistência entre JSON e XML.

5. Escolha do Formato (XML ou JSON)
Implementei botões de rádio no menu principal para permitir que o usuário escolha o formato (XML ou JSON) para carregar os dados.

Criei uma classe AppState para gerenciar o estado compartilhado dessa escolha.
Configurei as controllers para carregar os arquivos no formato escolhido, mas mantive a funcionalidade de salvar e atualizar simultaneamente nos dois formatos.
6. Adaptação para Outras Classes
Após consolidar o suporte a XML para pacientes, apliquei a mesma lógica às demais classes, ajustando os atributos e métodos conforme necessário.

7. Melhorias na Interface de Registro de Consultas
Para melhorar a usabilidade, substituí os campos de texto para IDs de médicos e pacientes por caixas de seleção (ChoiceBox), que exibem tanto o ID quanto o nome. Isso tornou o registro de consultas mais intuitivo.

8. Implementação das Caixas de Seleção
Atualizei o controller da tela de registro para incluir as ChoiceBoxes.
Criei métodos para preencher as caixas de seleção no método initialize(), utilizando o serviço de carregamento para obter os dados formatados.
Modifiquei o método registrarConsulta() para obter apenas o ID selecionado na ChoiceBox, minimizando alterações nos outros métodos.
Essa atualização envolveu várias melhorias no sistema, tanto em funcionalidade quanto em usabilidade, atendendo às demandas de persistência em XML e uma interface mais acessível para o usuário.
