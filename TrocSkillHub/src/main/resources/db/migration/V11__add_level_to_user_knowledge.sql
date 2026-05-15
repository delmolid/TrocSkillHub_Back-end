-- Move level field to user knowledge 
ALTER TABLE "knowledge" DROP COLUMN "level";

ALTER TABLE "user_knowledge" ADD COLUMN "level" VARCHAR(50);

