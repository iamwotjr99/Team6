'use client';

import { useEffect, useState } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';
import { useKakaoAuth } from '@/hooks/useKakaoAuth';
import { useAuthStore } from '@/stores/Auth';
import { loginWithKakao } from '@/app/api/auth';

export default function KakaoCallbackPage() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const code = searchParams.get('code');
  const { handleKakaoCallback } = useKakaoAuth();

  const setKakaoEmail = useAuthStore.use.setKakaoEmail();
  const setAccessToken = useAuthStore.use.setAccessToken();
  const setKakaoId = useAuthStore.use.setKakaoId();

  const [error, setError] = useState(false);

  useEffect(() => {
    const processKakaoLogin = async () => {
      if (!code) return;

      try {
        const { kakaoEmail, isRegistered, kakaoId } = await handleKakaoCallback(code);
        setKakaoEmail(kakaoEmail);
        setKakaoId(kakaoId); 

        if (isRegistered) {
          const { accessToken } = await loginWithKakao(kakaoEmail);
          setAccessToken(accessToken);
          router.replace('/chat');
        } else {
          router.replace('/signup');
        }
      } catch (error) {
        console.error('Kakao login error:', error);
        setError(true);
      }
    };

    processKakaoLogin();
  }, [
    code,
    handleKakaoCallback,
    router,
    setKakaoEmail,
    setAccessToken,
    setKakaoId,
  ]);

  return (
    <div className="flex h-screen items-center justify-center">
      {error ? (
        <p className="text-red-500">로그인에 실패했습니다. 다시 시도해주세요.</p>
      ) : (
        <p className="text-green-600">카카오 계정 확인 중입니다...</p>
      )}
    </div>
  );
}