while ($true) {
    Write-Output "Running update at $(Get-Date)"
    docker exec -u postgres pg-primary psql -d mydb -f /update_loop.sql
    Start-Sleep -Milliseconds 200
}
