import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import createSelectors from '.';
import { generateBaseNickname } from '@/utils/NicknameGenerator';
import { checkNickname } from '@/utils/checkNickname';


type NicknameState = {
  nickname: string;
  setNickname: (nick: string) => void;
  generateNickname: () => Promise<string>;
  checkNicknameOnly: () => Promise<boolean>;
  clearNickname: () => void; 
};

const store = create<NicknameState>()(
  persist(
    (set, get) => ({
      nickname: '',
      setNickname: (nick) => set({ nickname: nick }),

      generateNickname: async () => {
        const nickname = generateBaseNickname();
        set({ nickname });
        return nickname;
      },

      checkNicknameOnly: async () => {
        const currentNickname = get().nickname;
        const isDuplicate = await checkNickname(currentNickname);
        return isDuplicate;
      },

      clearNickname: () => set({ nickname: '' }),
    }),
    {
      name: 'nickname-storage',
    }
  )
);

export const useNicknameStore = createSelectors(store);