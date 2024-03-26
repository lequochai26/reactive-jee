import App from './rApp.js'

async function main() {
    window.app = new App();
}

main()
.then(
    function () {
        "rindex.js initialized successfully!"
    }
)
.catch(
    function (error) {
        console.error(error)
    }
);