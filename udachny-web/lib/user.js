export function isLogged() {
    if (getToken('jwt')) {
        return true
    } else {
        return false
    }
}

export function getToken() {
    return getCookie('jwt')
}

export function deleteToken() {
    deleteCookie('jwt')
}


function getCookie(key) {
    if (typeof document !== "undefined") {
        let name = key + "=";
        let ca = document.cookie.split(';');
        for (let i = 0; i < ca.length; i++) {
            let c = ca[i];
            while (c.charAt(0) == ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
            }
        }
    }
    return "";
}

function setCookie(key, value, exdays) {
    const d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    let expires = "expires=" + d.toUTCString();
    document.cookie = key + "=" + value + ";" + expires + ";path=/";
}

function deleteCookie(key) {
    setCookie(key, '', 0)
}