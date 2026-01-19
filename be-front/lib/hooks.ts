'use client';

import { useState, useEffect, useCallback } from 'react';

// Hook générique pour les appels API
export function useApi<T>(
  fetchFn: () => Promise<T>,
  dependencies: unknown[] = []
) {
  const [data, setData] = useState<T | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const refetch = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const result = await fetchFn();
      setData(result);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Erreur inconnue');
    } finally {
      setLoading(false);
    }
  }, [fetchFn]);

  useEffect(() => {
    refetch();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, dependencies);

  return { data, loading, error, refetch };
}

// Hook pour les mutations (POST, PUT, DELETE)
export function useMutation<T, R>(
  mutationFn: (data: T) => Promise<R>
) {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const mutate = async (data: T): Promise<R | null> => {
    setLoading(true);
    setError(null);
    try {
      const result = await mutationFn(data);
      return result;
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Erreur inconnue');
      return null;
    } finally {
      setLoading(false);
    }
  };

  return { mutate, loading, error };
}

// Hook pour l'authentification
interface UserData {
  id: number;
  username: string;
  email: string;
  role: string;
  prenom?: string;
  nom?: string;
}

function getStoredUser(): UserData | null {
  if (typeof window === 'undefined') return null;
  const stored = localStorage.getItem('user');
  if (stored) {
    try {
      return JSON.parse(stored);
    } catch {
      return null;
    }
  }
  return null;
}

export function useAuth() {
  const [user, setUser] = useState<UserData | null>(getStoredUser);

  const login = (userData: UserData) => {
    setUser(userData);
    localStorage.setItem('user', JSON.stringify(userData));
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem('user');
  };

  return { user, login, logout, isAuthenticated: !!user };
}
