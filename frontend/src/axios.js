import axios from "axios";

const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL || "http://localhost:8080/api",
  auth: {
    username: "admin", // Replace with your actual username
    password: "admin123", // Replace with your actual password
  },
});

export default api;
