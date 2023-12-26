stompClient.subscribe('/topic/refresh', function (message) {
    if (message.body === 'refresh') {
        // Refresh the page or part of it
        location.reload(); // For full page reload
        // Or implement logic to refresh only part of the page
    }
});