<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="tr">
<head>
  <meta charset="UTF-8">
  <title>${adminPanel}</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

  <style>
    body {
      margin: 0;
      background-color: #f8f9fa;
    }

    .sidebar {
      position: fixed;
      top: 0;
      left: 0;
      height: 100vh;
      width: 220px;
      background-color: #343a40;
      color: #fff;
      transition: width 0.3s ease;
      padding-top: 60px;
      z-index: 1000;
      overflow-x: hidden;
    }

    .sidebar.collapsed {
      width: 70px;
    }

    .sidebar .nav-link {
      color: #fff;
      white-space: nowrap;
    }

    .sidebar .nav-link .link-text {
      margin-left: 10px;
      transition: opacity 0.3s ease;
    }

    .sidebar.collapsed .link-text {
      opacity: 0;
    }

    .content {
      margin-left: 220px;
      padding: 30px;
      transition: margin-left 0.3s ease;
    }

    .content.collapsed {
      margin-left: 70px;
    }
    .dropdown-menu {
      background-color: #343a40;
      border: none;
    }

    .dropdown-item:hover {
      background-color: #495057;
    }
    .toggle-btn {
      position: fixed;
      top: 15px;
      left: 230px;
      z-index: 1100;
      transition: left 0.3s ease;
    }

    .toggle-btn.collapsed {
      left: 80px;
    }

    @media (max-width: 768px) {
      .sidebar {
        left: -220px;
      }

      .sidebar.show {
        left: 0;
      }

      .content {
        margin-left: 0 !important;
      }

      .toggle-btn {
        left: 10px !important;
      }
    }
  </style>
</head>
<body>

<!-- Toggle Button -->

<!-- Sidebar -->
<div class="sidebar d-flex flex-column p-3" id="sidebar">
  <h4 class="text-white text-center mb-4 link-text">${adminPanel}</h4>

  <ul class="nav nav-pills flex-column">
    <li class="nav-item">
      <a href="/admin/PersonInfo" class="nav-link text-white">
        👥 <span class="link-text">${memberTransactions}</span>
      </a>
    </li>
    <li class="nav-item">
      <a href="/admin/BookInfo" class="nav-link text-white">
        📨 <span class="link-text">${bookTransaction}</span>
      </a>
    </li>
  </ul>

  <button class="btn btn-secondary toggle-btn mt-3" id="toggleBtn" onclick="toggleSidebar()">☰</button>
</div>


<!-- Content -->
<div class="content" id="content">
  <jsp:include page="${body}" />
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
  function toggleSidebar() {
    const sidebar = document.getElementById('sidebar');
    const content = document.getElementById('content');
    const toggleBtn = document.getElementById('toggleBtn');

    sidebar.classList.toggle('collapsed');
    content.classList.toggle('collapsed');
    toggleBtn.classList.toggle('collapsed');
  }
</script>

</body>
</html>
