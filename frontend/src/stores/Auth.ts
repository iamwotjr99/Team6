import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import createSelectors from '.';

type AuthState = {
  accessToken: string | null;
  kakaoEmail: string | null;
  kakaoId: number | null; 
  setAccessToken: (token: string) => void;
  setKakaoEmail: (email: string) => void;
  setKakaoId: (id: number) => void;
  clearAuth: () => void;
};

const store = create<AuthState>()(
  persist(
    (set) => ({
      accessToken: null,
      kakaoEmail: null,
      kakaoId: null, 
      setAccessToken: (token) => set({ accessToken: token }),
      setKakaoEmail: (email) => set({ kakaoEmail: email }),
      setKakaoId: (id) => set({ kakaoId: id }), 
      clearAuth: () => set({ accessToken: null, kakaoEmail: null, kakaoId: null }),
    }),
    {
      name: 'auth-storage', 
    }
  )
);

export const useAuthStore = createSelectors(store);