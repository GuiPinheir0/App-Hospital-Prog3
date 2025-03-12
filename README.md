Nome: Guilherme Pinheiro 
Link para o video: https://drive.google.com/file/d/16EzOLwlEZ9ltpI5HT-yZnUO7n_KUakO4/view?usp=sharing


Introdução
O aplicativo foi desenvolvido com o objetivo de gerenciar pacientes, consultas médicas e outros dados relacionados ao ambiente hospitalar. A aplicação permite o registro, edição, exclusão e consulta de informações, integrando funcionalidades para melhorar a usabilidade e acessibilidade aos dados.


Estrutura do Aplicativo
O sistema foi desenvolvido em Java, utilizando a biblioteca JavaFX para a interface gráfica. A aplicação é organizada em diferentes telas, cada uma com responsabilidades específicas, e os dados são manipulados por meio de serviços que interagem com arquivos JSON para persistência.

Relato do Processo de Desenvolvimento

1. Criação do Projeto e Estrutura Inicial
Iniciei o projeto criando uma aplicação Maven com suporte a FXML, que já continha uma estrutura básica na Main e duas telas padrão. O desenvolvimento começou com a definição de uma estrutura modular, organizando as classes em pacotes específicos:

models: Contém as classes de dados, como Paciente e ConsultaMedica.
services: Responsáveis pela lógica de persistência e carregamento de dados.
controllers: Gerenciam a interação entre a interface gráfica (FXML) e os modelos.
Minha primeira tarefa foi estruturar os modelos, definindo atributos, construtores, getters e setters.

2. Primeira Tela: Cadastro de Pacientes
Minha ideia inicial era criar um menu principal com quatro opções: Pacientes, Médicos, Enfermeiros e Consultas Médicas. No entanto, decidi começar implementando apenas a tela de Pacientes, inicialmente com um botão que levava à tela de cadastro. A lógica por trás dessa decisão foi entender e implementar primeiro a funcionalidade de cadastro, já que isso serviria como base para o restante do projeto.

Após montar a tela no Scene Builder, trabalhei no controlador:

Configurei os TextFields para capturar as informações do usuário, adicionando validações apropriadas.
As informações foram armazenadas em variáveis locais com tipos correspondentes.
Essas variáveis foram usadas para instanciar objetos da classe Paciente com os dados fornecidos.
O paciente recém-criado foi então adicionado a um ArrayList.


3. Persistência de Dados com JSON
Um dos maiores desafios foi implementar a persistência de dados. Usei a biblioteca Gson para gravar os pacientes em um arquivo .json, mas isso exigiu várias tentativas e pesquisas. Para solucionar o problema, criei:

Um método carregar, que carrega os pacientes do arquivo JSON para o ArrayList ao iniciar a tela.
Um método salvar, que reescreve o arquivo JSON após alterações.
Inicialmente, esses métodos estavam nos controladores, mas depois os reorganizei no pacote services para melhor modularidade.

4. Tela Central de Pacientes
Depois de concluir o cadastro, implementei a Tela Central de Pacientes, que inclui:

Uma tabela para exibir todas as informações dos pacientes.
Botões para excluir e editar registros.
O processo envolveu:

Configurar a TableView com o método setCellValueFactory para mapear atributos dos objetos para as colunas da tabela.
Ajustar o arquivo module-info para corrigir erros de execução relacionados ao JavaFX.
Os botões foram implementados da seguinte forma:

Excluir: A funcionalidade foi simples, já que a TableView permite selecionar diretamente um item. O paciente selecionado era removido do ArrayList, o arquivo JSON era atualizado, e a tabela era recarregada.
Editar: Foi mais desafiador. Criei uma nova tela semelhante à de cadastro, mas com os campos preenchidos pelos dados do paciente selecionado. Para salvar as edições, implementei uma função de atualização, que localizava o paciente pelo ID no ArrayList, atualizava os dados e então salvava no arquivo.


5. Extensão para Médicos e Enfermeiros
Com a lógica de pacientes concluída, adaptei o sistema para gerenciar Médicos e Enfermeiros. A estrutura foi praticamente idêntica, com alterações apenas nos atributos específicos de cada classe.

6. Consultas Médicas e Relacionamento com Pacientes
A implementação das Consultas Médicas trouxe um novo desafio: estabelecer o relacionamento entre pacientes e consultas. Cada paciente possui um atributo historicoConsultasMedicas, que é uma lista de objetos do tipo ConsultaMedica.

Para lidar com isso:

No controller de registro de consultas, capturei o ID do paciente e a nova consulta criada.
Implementei um loop para localizar o paciente correspondente no ArrayList e adicionei a nova consulta ao histórico.
O arquivo JSON de pacientes foi então atualizado com o novo estado.
Essa lógica foi adaptada para todas as telas que lidavam com consultas, garantindo consistência entre pacientes e seus históricos.

7. Histórico de Consultas por Paciente
Na etapa final, adicionei um botão extra na Tela Central de Pacientes para abrir uma tabela separada exibindo o histórico de consultas de um paciente selecionado.

A lógica filtra as consultas com base no ID do paciente e exibe apenas os registros relevantes.
Essa abordagem tornou a interface mais organizada, evitando sobrecarregar a tela principal de pacientes.
Conclusão
O desenvolvimento do aplicativo foi um processo incremental, começando pelas funcionalidades mais simples e progredindo para as mais complexas. Cada etapa trouxe novos desafios, desde a integração entre telas até a manipulação de dados com JSON, mas as soluções implementadas tornaram o sistema funcional e escalável.
