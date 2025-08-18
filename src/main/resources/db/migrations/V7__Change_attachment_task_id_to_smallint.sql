-- Step 1: Drop the foreign key constraint (if it exists)
ALTER TABLE attachments DROP CONSTRAINT IF EXISTS attachments_task_id_fkey;

-- Step 2: Drop the column
ALTER TABLE attachments DROP COLUMN task_id;

-- Step 3: Re-add the column with the correct data type (SMALLINT)
ALTER TABLE attachments ADD COLUMN task_id SMALLINT;

-- Step 4: Re-add the foreign key constraint
ALTER TABLE attachments
    ADD CONSTRAINT attachments_task_id_fkey
        FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE;
