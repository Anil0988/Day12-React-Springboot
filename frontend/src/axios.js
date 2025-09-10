import axios from "axios";

// ⚠️ For now we hardcode login as "admin"
// Later, you can replace this with dynamic login (JWT recommended)
const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL || "http://localhost:8080/api", // use env variable or fallback to localhost
  auth: {
    username: "admin", // 👈 replace with your actual admin username
    password: "admin123", // 👈 replace with the actual password
  },
});

export default api;
