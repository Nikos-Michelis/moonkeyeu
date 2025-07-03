import axios from 'axios';
const api = axios.create({
    headers: {
        "Content-Type": "application/json",
    },
    withCredentials: true,
    Csrf: true,
    timeout: 60000
});

const handlePost = async (url, data, options) => {
    const response = await api.post(url, data, {...options});
    return response.data;
}
const handleGet = async (url, options ) => {
    const response = await api.get(url, {...options});
    return response.data;
}
const handlePut = async (url, data, options) => {
    const response = await api.put(url, data, {...options});
    return response.data;
}
const handleDelete = async (url, options) => {
    const response = await api.delete(url, {...options});
    return response.data;
}

export { api, handlePost, handleGet, handlePut, handleDelete };
