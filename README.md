MovInNantes
============

L'application Move in Nantes propose aux utilisateurs de connaitre le parcours d'un trajet entre deux adresses de leur choix au sein de l'agglomération de Nantes.

Elle propose les parcours :
- en voiture
- à pied
- à vélo
- en transport en commun

Les données utilisées pour ces différentes fonctionnalités sont celles de l'API de Google Maps (voiture, piéton, vélo) et de la TAN (transport en commun).

L'application utilise également les données open-data de Nantes pour l'affichage des parkings les plus proches de la destination lors d'un trajet en voiture.

Dépendances
===========

appengine >= 1.9.6
jackson-mapper-asl >= 1.9.13
opencsv >= 2.0
httpclient >= 4.3.4
jdom2 >= 2.0.5

Installation
============

Importer les dépendences et installer le package localement :
```
call mvn clean install
```

Déployer le projet sur Google App Engine :
```
cd guestbook-ear
call mvn appengine:update
```

Authors
=======

Etienne Landais - [horoks] (https://github.com/horoks)
Bastien Perdriau - [BPerdriau] (https://github.com/BPerdriau)
John Gonggrijp Dowe - [JohnGd] (https://github.com/JohnGd)

