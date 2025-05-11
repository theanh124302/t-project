UPDATE track_login
SET screen_size = 
    CASE 
        WHEN screen_size = '1080x1920' THEN '720x1280'
        ELSE '1080x1920'
    END,
    created_at = NOW()
WHERE id IN (
    SELECT id
    FROM track_login
    ORDER BY random()
    LIMIT 100
);
