import { create } from 'zustand';
import createSelectors from '.';
import { generateBaseNickname } from '@/utils/NicknameGenerator';
// import { checkNickname } from '@/utils/checkNickname';

type NicknameState = {
  nickname: string;
  setNickname: (nick: string) => void;
  generateNickname: () => Promise<string>;
};

const store = create<NicknameState>()((set) => ({
  nickname: '',
  setNickname: (nick) => set({ nickname: nick }),

  generateNickname: async () => {
    let base = generateBaseNickname();
    let candidate = base;
    let suffix = 1;

    // while (await checkNickname(candidate)) {
    //   candidate = `${base}${suffix}`;
    //   suffix++;
    // }

    set({ nickname: candidate });
    return candidate;
  },
}));

export const useNicknameStore = createSelectors(store);
