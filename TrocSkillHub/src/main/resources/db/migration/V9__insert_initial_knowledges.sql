-- Insertion des savoir-faire (knowledges) par catégorie

-- Catégorie 1 : Informatique
INSERT INTO "knowledge" ("name", "level", "category_id") VALUES 
    ('Java', 'Intermédiaire', 1),
    ('Python', 'Débutant', 1),
    ('JavaScript', 'Avancé', 1),
    ('React', 'Intermédiaire', 1),
    ('Spring Boot', 'Avancé', 1),
    ('SQL', 'Intermédiaire', 1),
    ('Git & GitHub', 'Débutant', 1),
    ('Docker', 'Débutant', 1);

-- Catégorie 2 : Cuisine
INSERT INTO "knowledge" ("name", "level", "category_id") VALUES 
    ('Cuisine italienne', 'Intermédiaire', 2),
    ('Pâtisserie', 'Débutant', 2),
    ('Cuisine végétarienne', 'Avancé', 2),
    ('Cuisine asiatique', 'Intermédiaire', 2),
    ('Boulangerie', 'Débutant', 2);

-- Catégorie 3 : Langues
INSERT INTO "knowledge" ("name", "level", "category_id") VALUES 
    ('Anglais', 'Avancé', 3),
    ('Espagnol', 'Intermédiaire', 3),
    ('Allemand', 'Débutant', 3),
    ('Chinois', 'Débutant', 3),
    ('Arabe', 'Intermédiaire', 3);

-- Catégorie 4 : Bricolage
INSERT INTO "knowledge" ("name", "level", "category_id") VALUES 
    ('Plomberie', 'Intermédiaire', 4),
    ('Électricité', 'Débutant', 4),
    ('Menuiserie', 'Avancé', 4),
    ('Peinture', 'Intermédiaire', 4);

-- Catégorie 5 : Musique
INSERT INTO "knowledge" ("name", "level", "category_id") VALUES 
    ('Guitare', 'Intermédiaire', 5),
    ('Piano', 'Débutant', 5),
    ('Chant', 'Avancé', 5),
    ('Batterie', 'Intermédiaire', 5),
    ('Solfège', 'Débutant', 5);

-- Catégorie 6 : Sport
INSERT INTO "knowledge" ("name", "level", "category_id") VALUES 
    ('Yoga', 'Intermédiaire', 6),
    ('Escalade','Débutant',6),
    ('Musculation', 'Avancé', 6),
    ('Course à pied', 'Débutant', 6),
    ('Natation', 'Intermédiaire', 6),
    ('Cyclisme','Débutant',6),
    ('Boxe', 'Débutant', 6);

-- Catégorie 7 : Jardinage
INSERT INTO "knowledge" ("name", "level", "category_id") VALUES 
    ('Potager bio', 'Intermédiaire', 7),
    ('Jardinage ornamental', 'Débutant', 7),
    ('Permaculture', 'Avancé', 7),
    ('Taille des arbres', 'Intermédiaire', 7);

-- Catégorie 8 : Arts
INSERT INTO "knowledge" ("name", "level", "category_id") VALUES 
    ('Dessin', 'Intermédiaire', 8),
    ('Peinture à l''huile', 'Avancé', 8),
    ('Photographie', 'Intermédiaire', 8),
    ('Couture', 'Débutant', 8),
    ('Poterie', 'Débutant', 8);

-- Catégorie 9 : Éducation
INSERT INTO "knowledge" ("name", "level", "category_id") VALUES 
    ('Mathématiques', 'Avancé', 9),
    ('Physique', 'Intermédiaire', 9),
    ('Histoire', 'Avancé', 9),
    ('Aide aux devoirs primaire', 'Intermédiaire', 9);

-- Catégorie 10 : Bien-être
INSERT INTO "knowledge" ("name", "level", "category_id") VALUES 
    ('Massage', 'Intermédiaire', 10),
    ('Méditation', 'Avancé', 10),
    ('Nutrition', 'Intermédiaire', 10),
    ('Sophrologie', 'Débutant', 10);

-- Catégorie 11 : Design
INSERT INTO "knowledge" ("name", "level", "category_id") VALUES 
    ('UI/UX Design', 'Intermédiaire', 11),
    ('Graphic Design', 'Avancé', 11),
    ('Web Design', 'Intermédiaire', 11),
    ('Design Thinking', 'Débutant', 11),
    ('Figma', 'Intermédiaire', 11),
    ('Adobe Photoshop', 'Avancé', 11),
    ('Adobe Illustrator', 'Intermédiaire', 11),
    ('Canva', 'Débutant', 11),
    ('Branding & Identité visuelle', 'Avancé', 11),
    ('Motion Design', 'Débutant', 11);

-- Catégorie 12 : Recherche scientifique
INSERT INTO "knowledge" ("name", "level", "category_id") VALUES 
    ('Méthodologie de recherche', 'Avancé', 12),
    ('Analyse statistique', 'Intermédiaire', 12),
    ('Rédaction scientifique', 'Avancé', 12),
    ('Biologie moléculaire', 'Intermédiaire', 12),
    ('Chimie organique', 'Avancé', 12),
    ('Physique quantique', 'Débutant', 12),
    ('Écologie', 'Intermédiaire', 12),
    ('Astronomie', 'Débutant', 12),
    ('Data Science & Machine Learning', 'Avancé', 12),
    ('Laboratoire & Expérimentation', 'Intermédiaire', 12);
