PRO3600_21_HEN_09

L'art du cheminement

Jean LE BERRE
Tangi LE GAC
Denzel LORGE
Joshua RANDRIA

Tuteur : Pascal HENNEQUIN

Présentation : Un logiciel de visualisation d'algorithme de pathfinding

Utilisation :
	Séléctionner les paramètres sur la page d'acceuil puis lancer la génération.

Changement depuis le 21/05/21 :
	26/05/21 : rajout du choix de séléction du départ et de l'arrivée
				-> PageAcceuilCtrl.eviterErreurDepartArrivee() / TemplatePageAcceuil / arbreLabyrinthe.departAleatoire() l.96
	26/05/21 : rajout de la visualisation comparatif
				->PageResolutionCtrl l52-121 / TemplatePageResolution
	26/05/2021 rajout de l'option "Faire avec test"
				-> PageAcceuilCtrl l130.151
	29/05/2021 rajout de l'algoritme du mur droit
				-> package model.mur_droit
				
Tips
	Utilisation de GitLab:
	Connexion avec son terminal => $ git clone git@gitlabens.imtbs-tsp.eu:jean.le_berre/pro3600_21_hen_09.git
	
	Rajouter/MAJ un fichier/document sur une nouvelle branche :
		$ git checkout -b nomBranche 
		$ git add nomFichier
		$ git commit -m "messageDescriptif,documents/fichiersModifiées"
	
	Mettre sur le master :
		$ git checkout master
		$ git merge --no-ff nomBranche -m "MessageDescriptif,Merge branch nomBranche"
	
	Mise à jour du dépot GitLab :
		$ git push origin master
	
	Mise à jour local :
		$ git pull origin
		
	Visualisé le dépot :
		$ git log --graph --oneline --decorate
	ou si installé 
		$ gitg	
	
		
	
