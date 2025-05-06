'use client';

import { useRouter } from 'next/navigation';
import { getKakaoAuthUrl } from '@/utils/Kakao';

export function useKakaoAuth() {
  const router = useRouter();

  const redirectToKakaoLogin = () => {
    window.location.href = getKakaoAuthUrl();
  };

  const handleKakaoCallback = async (code: string): Promise<{ kakaoEmail: string; isRegistered: boolean, kakaoId: number; }> => {
    try {
      const res = await fetch('http://3.38.153.246:8080/api/auth/oauth/kakao/check', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify({ code }),
      });

      if (!res.ok) {
        const errorText = await res.text();
        console.error('카카오 인증 실패:', errorText);
        throw new Error('카카오 인증 실패');
      }

      const result = await res.json();
      console.log('[handleKakaoCallback 결과]:', result);

      if (!result.success || !result.data) {
        throw new Error('카카오 인증 데이터 없음');
      }

      const { kakaoEmail, isRegistered, kakaoId } = result.data;

      return { kakaoEmail, isRegistered, kakaoId };
    } catch (error) {
      console.error('handleKakaoCallback 에러:', error);
      throw error;
    }
  };

  return { redirectToKakaoLogin, handleKakaoCallback };
}