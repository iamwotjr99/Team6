export function getKakaoAuthUrl(): string {
  const base = 'https://kauth.kakao.com/oauth/authorize';
  const params = new URLSearchParams({
    client_id: process.env.NEXT_PUBLIC_KAKAO_REST_API_KEY!,
    redirect_uri: process.env.NEXT_PUBLIC_KAKAO_REDIRECT_URI!,
    response_type: 'code',
  });
  return `${base}?${params.toString()}`;
}