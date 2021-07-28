# EstoqueSDP-App

Aplicativo para localização e visualização de produtos relacionados a loja de calçados.

![ Alt text](https://github.com/samirmaciel/EstoqueSDP-App/blob/master/estoque.gif) [](estoque.gif)


#### Funcionalidades: 

* Busca de produtos por código
* Cadastro ou atualizção de produtos
* Visualização de produtos totais do estoque e por categoria


#### Conexão com o FireBase:

##### Padrão Singleton - Apenas uma **instância** da classe de conexão é criada para toda aplicação, e para isso, é preiciso extende la para Application e declará la no Manifest.

* [Manifest](https://github.com/samirmaciel/EstoqueSDP-App/blob/master/app/src/main/AndroidManifest.xml)
* [Conexão com FireBase](https://github.com/samirmaciel/EstoqueSDP-App/blob/master/app/src/main/java/com/samirmaciel/estoquesdp/banco/FireBaseBanco.java)
