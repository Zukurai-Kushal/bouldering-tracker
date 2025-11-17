import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:9001', // Spring Boot backend
  withCredentials: true // for session cookies
});

export default api;