# TrocSkillHub_Back-end
langage : Java SpringBoot
Bdd: Postgresql

# Build pour docker si y'a changement:
docker compose up --build

# Suite à chaque nouvelle migration, il faudra faire
docker compose down -v

# Lancer docker: !! penser à démarrer l'appli docker puis lancer la commande suivante
docker compose up

# pour voir si l'app a démarré
http://localhost:8080

# Quand dev terminé, penser à arreter les conteneurs
docker compose down