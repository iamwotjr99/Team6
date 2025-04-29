const adjs = [
  '아름다운', '빠른', '조용한', '즐거운', '기분좋은',
  '똑똑한', '용감한', '귀여운', '행복한', '신비한',
  '따뜻한', '찬란한', '화려한', '섬세한', '부드러운',
  '강한', '넓은', '깊은', '밝은', '청명한',
  '시원한', '고요한', '풍부한', '달콤한', '상쾌한',
  '짙은', '투명한', '환한', '은은한', '빨간',
  '푸른', '젊은', '늙은', '굳센', '순수한',
  '열정적인', '성실한', '유쾌한', '감미로운', '아련한',
  '단단한', '부지런한', '명랑한', '깊은', '사려깊은',
  '활기찬', '강렬한', '평화로운', '풍성한', '튼튼한',
];

const nouns = [
  '고양이', '달', '별', '구름', '사자',
  '여우', '토끼', '강아지', '숲', '바다',
  '산', '강', '꽃', '나무', '바람',
  '하늘', '호수', '벌레', '돌', '길',
  '새', '바위', '눈', '비', '햇살',
  '모래', '파도', '섬', '들판', '계곡',
  '불꽃', '별빛', '연꽃', '열매', '나비',
  '동굴', '폭포', '언덕', '나뭇잎', '겨울',
  '여름', '봄', '가을', '달빛', '별똥별',
];

// 닉네임 생성함수
export function generateBaseNickname(): string {

  const totalCombos = adjs.length * nouns.length;
  const r = Math.random() * (totalCombos * 9000);

  const comboIndex = Math.floor(r / 9000);
  const suffixRand = Math.floor(r % 9000);

  const adjIndex = comboIndex % adjs.length;
  const nounIndex = Math.floor(comboIndex / adjs.length);
  const randomN = 1000 + suffixRand; 

  return `${adjs[adjIndex]}${nouns[nounIndex]}${randomN}`;
}
