while ($true) {
    Write-Output "Running insert at $(Get-Date)"
    docker exec -u postgres pg-primary psql -d mydb -f /insert_loop.sql
    Start-Sleep -Milliseconds 200
}
