'use client';

import Button from '../common/Button';
import Input from '../common/Input';
import SubHeader from '../layout/header/SubHeader';
import { SiCodesandbox } from 'react-icons/si';
import SignupCheckModal from './SignupCheckModal';
import { useModalStore } from '@/stores/Modal';
import { useAgreeStore } from '@/stores/Agree';
import CheckIcon from '../icon/CheckIcon';
import { useEffect } from 'react';
import { useNicknameStore } from '@/stores/Nickname';

export default function Signup() {
  const setModal = useModalStore.use.setModal();

  const nickname = useNicknameStore.use.nickname();
  const generateNickname = useNicknameStore.use.generateNickname();

  // 체크박스 상태 불러오기
  const agreeAge = useAgreeStore.use.agreeAge();
  const toggleAge = useAgreeStore.use.toggleAge();

  const agreePrivacy = useAgreeStore.use.agreePrivacy();
  const togglePrivacy = useAgreeStore.use.togglePrivacy();

  const agreeTou = useAgreeStore.use.agreeTou();
  const toggleTou = useAgreeStore.use.toggleTou();

  useEffect(() => {
    generateNickname();
  }, []);

  // 닉네임 중복체크 함수
  const handleCheckNickname = () => {
    generateNickname();
  };

  // 회원가입 제출 함수
  const submitSigupForm = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setModal(<SignupCheckModal />);
  };

  return (
    <>
      <SubHeader text='회원가입' />
      <section className='h-full overflow-y-auto px-4 py-1'>
        <form className='pt-2 flex flex-col gap-3' onSubmit={submitSigupForm}>
          <label className='block text-sm font-semibold text-gray-700'>
            닉네임 생성
          </label>
          <div className='flex gap-2'>
            <Input className='border text-black' value={nickname} type='text' readOnly />
            <Button
              className='h-11 w-24 text-sm px-2'
              type='button'
              onClick={handleCheckNickname}
            >
              중복 확인
            </Button>
          </div>
          <div className='flex flex-col gap-3'>
            <div className='flex gap-1' onClick={toggleAge}>
              {agreeAge ? (
                <CheckIcon className='w-5 h-5 border border-black' />
              ) : (
                <SiCodesandbox
                  size={20}
                  color='white'
                  className='flex border border-black'
                />
              )}
              <div className='text-sm flex items-center'>
                <span className='font-semibold pr-1'>[필수]</span>만 14세 이상
              </div>
            </div>
            <div className='flex  gap-1' onClick={togglePrivacy}>
              {agreePrivacy ? (
                <CheckIcon className='w-5 h-5 text-gray-500  border border-black' />
              ) : (
                <SiCodesandbox
                  size={20}
                  color='white'
                  className='flex border border-black'
                />
              )}
              <div className='text-sm items-center flex'>
                <span className='font-semibold pr-1'>[필수]</span>
                개인정보처리방침
              </div>
            </div>
            <div className='flex gap-1' onClick={toggleTou}>
              {agreeTou ? (
                <CheckIcon className='w-5 h-5 text-gray-500 border border-black' />
              ) : (
                <SiCodesandbox
                  size={20}
                  color='white'
                  className='flex border border-black'
                />
              )}
              <div className='text-sm items-center flex'>
                <span className='font-semibold pr-1'>[필수]</span>이용약관
              </div>
            </div>
          </div>
          <Button type='submit' className='disabled:bg-gray-300 h-12'>
            회원가입 완료
          </Button>
        </form>
      </section>
    </>
  );
}
