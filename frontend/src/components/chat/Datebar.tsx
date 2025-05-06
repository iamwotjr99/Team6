type Props = {
  date: string;
};

export default function Datebar({ date }: Props) {
  return (
    <div className="my-4 flex items-center gap-4 text-center text-md text-gray-400">
      <div className="flex-grow border-t border-gray-300" />
      <span className="whitespace-nowrap">{date}</span>
      <div className="flex-grow border-t border-gray-300" />
    </div>
  );
}