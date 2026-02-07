document.addEventListener("DOMContentLoaded", () => {

    // ================= REGISTER =================
    const registerForm = document.getElementById("registerForm");
    if (registerForm) {
        registerForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            const userData = {
                name: document.getElementById("name").value.trim(),
                email: document.getElementById("email").value.trim(),
                password: document.getElementById("password").value.trim()
            };

            try {
                const res = await fetch("http://localhost:8080/api/users/register", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(userData)
                });

                if (res.ok) {
                    alert("Registration successful!");
                    window.location.href = "login.html";
                } else {
                    const msg = await res.text();
                    alert(msg);
                }
            } catch (err) {
                console.error(err);
                alert("Server error. Try again later.");
            }
        });
    }

    // ================= LOGIN =================
    const loginForm = document.getElementById("loginForm");
    if (loginForm) {
        loginForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            const loginData = {
                email: document.getElementById("email").value.trim(),
                password: document.getElementById("password").value.trim()
            };

            try {
                const res = await fetch("http://localhost:8080/api/users/login", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(loginData)
                });

                if (res.ok) {
                    const data = await res.json();
                    sessionStorage.setItem("user", JSON.stringify(data));
                    window.location.href = "index.html";
                } else {
                    alert("Invalid email or password");
                }
            } catch (err) {
                console.error(err);
                alert("Server error. Try again later.");
            }
        });
    }

    // ================= CREATE BLOG =================
    const createBlogForm = document.getElementById("createBlogForm");
    if (createBlogForm) {
        createBlogForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            const user = JSON.parse(sessionStorage.getItem("user"));
            if (!user || !user.userId) {
                window.location.href = "login.html";
                return;
            }

            const blogData = {
                title: document.getElementById("blogTitle").value.trim(),
                content: document.getElementById("blogContent").value.trim(),
                status: "PUBLISHED"
            };

            try {
                const res = await fetch(
                    `http://localhost:8080/api/blogs/${user.userId}`,
                    {
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify(blogData)
                    }
                );

                if (res.ok) {
                    window.location.href = "index.html";
                } else {
                    const msg = await res.text();
                    alert(msg);
                }
            } catch (err) {
                console.error(err);
                alert("Server error. Try again later.");
            }
        });
    }

    // ================= INDEX PAGE =================
    const blogList = document.getElementById("blogList");
    if (blogList) {
        loadAllBlogs();
    }

    async function loadAllBlogs() {
        try {
            const res = await fetch("http://localhost:8080/api/blogs");
            if (!res.ok) return;

            const blogs = await res.json();
            blogList.innerHTML = "";

            blogs.forEach(blog => {
                const div = document.createElement("div");
                div.className = "blog-card";
                div.innerHTML = `
                    <h3>${blog.title}</h3>
                    <p>${blog.content.substring(0, 120)}...</p>
                    <button onclick="location.href='blog.html?id=${blog.blogId}'">
                        Read More
                    </button>
                `;
                blogList.appendChild(div);
            });
        } catch (err) {
            console.error(err);
        }
    }

    // ================= SINGLE BLOG PAGE =================
    const blogTitleEl = document.getElementById("blogTitle");
    if (blogTitleEl) {
        loadSingleBlog();
    }

    async function loadSingleBlog() {
        const id = new URLSearchParams(window.location.search).get("id");
        if (!id) return;

        try {
            const res = await fetch("http://localhost:8080/api/blogs");
            if (!res.ok) return;

            const blogs = await res.json();
            const blog = blogs.find(b => b.blogId == id);
            if (!blog) return;

            document.getElementById("blogTitle").innerText = blog.title;
            document.getElementById("blogContent").innerText = blog.content;
            document.getElementById("blogAuthor").innerText = blog.author.name;
            document.getElementById("blogDate").innerText =
                new Date(blog.createdAt).toLocaleDateString();

            loadComments(id);
        } catch (err) {
            console.error(err);
        }
    }

    // ================= COMMENTS =================
    async function loadComments(blogId) {
        try {
            const res = await fetch(`http://localhost:8080/api/comments/${blogId}`);
            if (!res.ok) return;

            const comments = await res.json();
            const commentList = document.getElementById("commentList");
            if (!commentList) return;

            commentList.innerHTML = "";
            comments.forEach(c => {
                const div = document.createElement("div");
                div.className = "comment";
                div.innerHTML = `<b>${c.user.name}</b>: ${c.commentText}`;
                commentList.appendChild(div);
            });
        } catch (err) {
            console.error(err);
        }
    }

});
