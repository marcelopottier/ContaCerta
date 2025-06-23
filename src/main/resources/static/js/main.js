const loadPage = (page, element) => {
    fetch(page)
        .then(res => {
            if (!res.ok) throw new Error("Página não encontrada")
            return res.text()
        })
        .then(html => {
            document.getElementById("content").innerHTML = html;
        })
        .catch(err => {
            document.getElementById("content").innerHTML = "<p>Error loading page.</p>"
        })

    const links = document.querySelectorAll(".nav-links a")
    links.forEach(link => link.classList.remove("active"))

    element.classList.add("active");
}

// const darkMode = () => {
//     let element = document.body
//     element.classList.toggle("dark-mode")
// }