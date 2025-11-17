import { createContext, useState, useEffect } from 'react';
import { getCurrentUser } from '../api/userApi';

export const AuthContext = createContext();

export default function AuthProvider({ children }) {
  const [user, setUser] = useState(null);

  useEffect(() => {
    getCurrentUser()
      .then(res => setUser(res.data))
      .catch(() => setUser(null)); // Not logged in
  }, []);

  return (
    <AuthContext.Provider value={{ user, setUser }}>
      {children}
    </AuthContext.Provider>
  );
}