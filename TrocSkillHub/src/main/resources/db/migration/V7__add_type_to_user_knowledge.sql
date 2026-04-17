-- Ajouter une colonne pour distinguer compétence (SKILL) et besoin (NEED)
ALTER TABLE "user_knowledge" 
ADD COLUMN "type" VARCHAR(50) NOT NULL DEFAULT 'SKILL';

-- Ajouter une contrainte pour valider les valeurs
ALTER TABLE "user_knowledge"
ADD CONSTRAINT check_type CHECK ("type" IN ('SKILL', 'NEED'));