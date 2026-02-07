document.addEventListener("DOMContentLoaded", function() {
    // Dashboard dummy stats
    if (document.getElementById("totalUsers")) {
        document.getElementById("totalUsers").textContent = 150;
        document.getElementById("totalBlogs").textContent = 45;
        document.getElementById("pendingComments").textContent = 12;
    }

    // Users page dummy data
    if (document.getElementById("usersTableBody")) {
        const users = [
            {id:1, name:"Alice", email:"alice@mail.com", role:"USER"},
            {id:2, name:"Bob", email:"bob@mail.com", role:"ADMIN"},
            {id:3, name:"Charlie", email:"charlie@mail.com", role:"USER"}
        ];
        let rows = "";
        users.forEach(u => {
            rows += `<tr>
                        <td>${u.id}</td>
                        <td>${u.name}</td>
                        <td>${u.email}</td>
                        <td>${u.role}</td>
                        <td><button>Edit</button> <button>Delete</button></td>
                    </tr>`;
        });
        document.getElementById("usersTableBody").innerHTML = rows;
    }

    // Blogs page dummy data
    if (document.getElementById("blogsTableBody")) {
        const blogs = [
            {id:1, title:"My First Blog", author:"Alice", status:"PUBLISHED"},
            {id:2, title:"Spring Boot Tips", author:"Bob", status:"DRAFT"},
            {id:3, title:"JavaScript Tricks", author:"Charlie", status:"PUBLISHED"}
        ];
        let rows = "";
        blogs.forEach(b => {
            rows += `<tr>
                        <td>${b.id}</td>
                        <td>${b.title}</td>
                        <td>${b.author}</td>
                        <td>${b.status}</td>
                        <td><button>Edit</button> <button>Delete</button></td>
                    </tr>`;
        });
        document.getElementById("blogsTableBody").innerHTML = rows;
    }

    // Comments page dummy data
    if (document.getElementById("commentsTableBody")) {
        const comments = [
            {id:1, blog:"My First Blog", user:"Alice", text:"Great post!"},
            {id:2, blog:"Spring Boot Tips", user:"Bob", text:"Very helpful."},
            {id:3, blog:"JavaScript Tricks", user:"Charlie", text:"Nice article!"}
        ];
        let rows = "";
        comments.forEach(c => {
            rows += `<tr>
                        <td>${c.id}</td>
                        <td>${c.blog}</td>
                        <td>${c.user}</td>
                        <td>${c.text}</td>
                        <td><button>Delete</button></td>
                    </tr>`;
        });
        document.getElementById("commentsTableBody").innerHTML = rows;
    }
});
